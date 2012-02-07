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

(def ops ['+ '- '* '/])

(def err-status
  (concat (map #(+ 400 %) (range 19))
          (map #(+ 500 %) (range 10))))

(declare random-expr)

(defn num-or-op [rand-fn]
  (if (> 3 (rand-int 4))
    (inc (rand-fn 100))
    (random-expr)))

(defn random-expr
  ([] (random-expr rand-int))
  ([rand-fn] (let [a (num-or-op rand-fn)
                   b (num-or-op rand-fn)
                   op (rand-nth ops)
                   op-str (apply str [a " " op " " b])]
               (if (zero? (rand-int 2))
                 (str "( " op-str " )")
                 op-str))))

(defn build-url
  "Since url-encode is designed for query strings and replaces spaces by pluses, we take another step to replace those pluses with %20 for a valid url path."
  [op-str]
  (str url (str/replace (url-encode op-str) #"\+" "%20")))

(defn ^:dynamic wrapped-get
  "Allows dynamic rebinding of http/get requests"
  [& args]
  (println "wrapped " args)
  (apply http/get args))

(defn test-operation []
  (let [op-str (random-expr)]
    (println "testing " op-str)
    (is (= (-> op-str
               (build-url)
               (wrapped-get {:throw-exceptions false})
               (:body))
           (str (eval-math op-str))))))

(defn test-error []
  (is (< (:status (wrapped-get
                   (str url "/err/" (rand-nth err-status))
                   {:throw-exceptions false}))
         400)))

;;(run-jetty main-routes {:port portnum})