;;
;; Recoding HTTP with The Grinder
;;

(ns math.http
  (:import [net.grinder.script Grinder Test])
  (:use math.test)
  (:require [clj-http.client :as http])
  )
  
;; declare symbols used by the script
(let [grinder Grinder/grinder
      test (Test. 1 "HTTP")]

  ;; utility function for logging
  (defn log [& text]
    (.. grinder (getLogger) (output (apply str text))))

  ;; record calls to the http get function
  (.. test (record http/get))
  
  ;; the factory function
  ;; called once by each worker thread
  (fn []
 
    ;; the test runner function
    ;; called on each run
    (fn []

      ;; log the output of a random math request
      (let [op (random-operation)]
        (log op " = " (:body (http/get (build-url op)))))

      ) ;; end of test runner function
    ) ;; end of factory function
  ) ;; end of script let form