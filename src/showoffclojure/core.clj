(ns showoffclojure.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.pprint :refer (print-table)]
            [net.cgrand.enlive-html :as html]))

(def ^:const REDDIT-URL "http://reddit.com/r/clojure.json?limit=100")
(def ^:const headers {:headers {"User-Agent" "showoffclojure.core by vagmi"}})

;; this will have a structure like
;; {"infoq.com" 3 "asdf" 4}
(defonce domain-summary (ref {}))

;; this will ahve the following keys {:article_url, :reddit_title, :actual_title}
(defonce article-details (ref []))

(defn fetch-url [url]
  (-> url
      (client/get headers)
      (java.io.StringReader.)
      (html/html-resource)))

(defn extract-title [url]
  (try
    (first (map html/text
                (-> (fetch-url url)
                    (html/select [:html :head :title]))))
    (catch Exception e "unknown")
    (catch Error e "Unknown")))

(defn update-summary [entry]
  (let [title (extract-title (:url entry))
        domain-count (@domain-summary (:domain entry))]
    (dosync
      (if domain-count
        (alter domain-summary assoc (:domain entry) (inc domain-count))
        (alter domain-summary assoc (:domain entry) 1))
      (alter article-details conj {:url (:url entry)
                                   :reddit-title (:title entry)
                                   :actual-title title}))
    (print ".")
    (flush)))

(defn fetch-and-summarize [run-parallel?]
  (let [parse-json (comp #(json/read-str % :key-fn keyword)
                         :body
                         #(client/get % headers))
        map-fn (if run-parallel? pmap map)
        reddit-data (-> (parse-json REDDIT-URL)
                        (:data)
                        (:children))]
    (map-fn (comp update-summary :data) reddit-data)))


(defn -main [run-parallel?]
  (time
    (do
      (dorun (fetch-and-summarize (Boolean/parseBoolean run-parallel?)))
      (print-table (map (fn [[domain cnt]]
                          {:domain domain :count cnt})
                        (into [] @domain-summary)))
      (println "Fetched " (count @article-details) " articles")
      (shutdown-agents))))
