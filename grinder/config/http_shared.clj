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
      stats (.getStatistics grinder) ;; define stats here for easier referencing
      test (Test. 1 "Custom Stats")]

  (defn log [& text]
    (.. grinder (getLogger) (output (apply str text))))

  ;; utility to return the number operations op in a mathematical expression expr
  (defn count-op [op expr]
    (count (re-seq (re-pattern (str "\\" op)) expr)))
  
  (defn instrumented-get [expr]
    ;; use getForCurrentTest when recording stats within an instrumented function
    ;;(.. stats getForCurrentTest (setLong "userLong0" (count-op '+ expr)))
    ;;(.. stats getForCurrentTest (setLong "userLong1" (count-op '- expr)))
    (.. (HTTPRequest.) (GET (build-url expr))))

  (.. test (record instrumented-get))

  ;; create multiple tests
  ;; share among threads using atom
  ;; show custom property to switch behaviors
  
  (fn []
    
    (fn []
          
      ;; request using a recorded function
      (let [expr (random-expr)]
        
        ;; prevent reporting until after the test is called
        (.. stats (setDelayReports true))
        
        (instrumented-get expr)
        
        ;; record the stats
        (map-indexed (partial record-stat expr) ops)
        
        )
      )
    )
  )