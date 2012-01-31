(ns math.test
  (:use math.core)
  (:use ring.adapter.jetty)
  (:use [clj-http.util :only [url-encode url-decode]])
  (:use clojure.test)
  (:require [clj-http.client :as http])
  (:require [clojure.string :as str])
  )

(def portnum 9999)
(def url (str "http://localhost:" portnum "/"))

(def binary-functions ['+ '- '* '/])

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

(defn build-url [op-str]
  (str url (str/replace (url-encode op-str) #"\+" "%20")))

(defn test-operation []
  (let [op-str (random-operation)]
    (is (= (-> op-str
               (build-url)
               (http/get {:throw-exception false})
               (:body))
           (str (eval-math op-str))))))

;;(run-jetty main-routes {:port portnum})