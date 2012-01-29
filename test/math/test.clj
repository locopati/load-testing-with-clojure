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

(def binary-functions ['+ '- '* '/ '**])
(def unary-functions ['sin 'cos 'tan 'sq 'sqrt])

(defn random-operation []
  (let [a (rand-int 1000)
        b (rand-int 1000)
        op (rand-nth binary-functions)]
    (str/join " " (map str [a op b]))))

(defn build-url
  "url-encode is designed for query strings and replaces spaces by pluses - 
for url paths however, spaces must be %20"
  [op-str]
  (str url (str/replace (url-encode op-str) #"\+" "%20")))

(defn test-operation []
  (let [op-str (random-operation)
        expected (str (eval-math op-str))
        url (build-url op-str)
        actual (:body (http/get url))]
    (is (= expected actual))))

;;(run-jetty main-routes {:port portnum})