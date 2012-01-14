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

(def binary-functions ['+ '- '* '/ '**])
(def unary-functions ['sin 'cos 'tan 'sq 'sqrt])

(defn random-operation []
  (let [a (rand-int 1000000)
        b (rand-int 1000000)
        op (rand-nth binary-functions)]
    (str/join " " (map str [a op b]))))

(defn build-url [op-str]
  (str url (str/replace (url-encode op-str) #"\+" "%20")))

(defn test-operation []
  (let [op-str (random-operation)
        expected (str (eval-math op-str))
        url (build-url op-str)
        actual (:body (http/get url))]
    (is (= expected actual))))

;;(run-jetty main-routes {:port portnum})