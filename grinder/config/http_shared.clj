;;
;; Sharing tests across threads with The Grinder
;;

(ns math.http
  (:import [net.grinder.script Grinder Test]
           [net.grinder.plugin.http HTTPRequest])
  (:use math.test)
  (:require [clj-http.client :as http])
  )
  
(let [grinder Grinder/grinder
      stats (.getStatistics grinder)
      ;; here we use a custom property to indicate sharing among threads
      shared? (.. grinder getProperties (getBoolean "grinder.shared" false))
      test (Test. 1 "Custom Stats")]

  (defn log [& text]
    (.. grinder (getLogger) (output (apply str text))))

  ;; again the arity must match the rebound fn
  ;; and the return value converted
  (defn instrumented-get [url & _]
    (println "instrumented " url)
    {:body (.. (HTTPRequest.) (GET url) getText)})

  (.. test (record instrumented-get))

  (defn next-test [{_ :test-fn remaining :tests}]
    {:test-fn (first remaining) :tests (rest remaining)})
  
  (defn shared-tests [test-atom]
    (loop [{current-test :test-fn remaining-tests :tests}
           (swap! test-atom next-test)]
      (if (empty? remaining-tests)
        nil
        (do (current-test)
            (recur (swap! test-atom next-test))))))

  (fn []

    (println "worker")
    
    (fn []

      (println "run")
      
      ;; request using a recorded function
      (binding [wrapped-get instrumented-get]
        (println "binding")
        (let [test-atom (atom {:test-fn nil
                               :tests (repeat 100 test-operation)})]
          (println "let")
          (println test-atom)
          (println @test-atom)
          (println (count (:tests @test-atom)))
          
          (if shared?
            (shared-tests test-atom)
            (do (println "not shared")
                ;; this does not work
                (map #(%) (:tests @test-atom))
                ;; this does
                ((first (:tests @test-atom)))))
          
        
          )
        )
      )
    )
  )