(ns math.test
  (:use math.core
        ring.adapter.jetty
        [clj-http.util :only [url-encode url-decode]]
        clojure.test)
  (:require [clj-http.client :as http]
            [clojure.string :as str])
  )

(def portnum 9999)
(def url (str "http://localhost:" portnum "/"))

(def binary-functions ['+ '- '* '/])

(def err-status
  (concat (map #(+ 400 %) (range 19))
          (map #(+ 500 %) (range 10))))

(declare random-operation)

(defn num-or-op [rand-fn]
  (if (> 3 (rand-int 4))
    (rand-fn 100)
    (random-operation)))

(defn random-operation
  ([] (random-operation rand-int))
  ([rand-fn] (let [a (num-or-op rand-fn)
                   b (num-or-op rand-fn)
                   op (rand-nth binary-functions)
                   op-str (apply str [a " " op " " b])]
               (if (zero? (rand-int 2))
                 (str "( " op-str " )")
                 op-str))))

(defn build-url
  "url-encode is designed for query strings and replaces spaces by pluses - 
for url paths however, spaces must be %20"
  [op-str]
  (str url (str/replace (url-encode op-str) #"\+" "%20")))

(defn test-operation []
  (let [op-str (random-operation)]
    (is (= (-> op-str
               (build-url)
               (http/get {:throw-exceptions false})
               (:body))
           (str (eval-math op-str))))))

(defn test-error []
  (is (< (:status (http/get
                   (str url "/err/" (rand-nth err-status))
                   {:throw-exceptions false}))
         400)))

;;(run-jetty main-routes {:port portnum})