;;
;; Recoding HTTP with The Grinder
;;

(ns math.http
  (:import [net.grinder.script Grinder Test])
  (:use math.test)
  (:require [clj-http.client :as http])
  )
  
(let [grinder Grinder/grinder
      test (Test. 1 "HTTP")]

  (defn log [& text]
    (.. grinder (getLogger) (output (apply str text))))

  ;; record calls to the http get function
  (.. test (record http/get))
  
  (fn []
 
    (fn []

      ;; log the output of a random math request
      (let [op (random-expr)]
        (log op " = " (:body (http/get (build-url op)))))

      )
    )
  )