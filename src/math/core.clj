(ns math.core
	(:use clojure.set
              compojure.core
              incanter.core
              ring.middleware.bounce-favicon)
	(:require [compojure.route :as route]
                  [compojure.handler :as handler])
)

(defn eval-math [expr-str]
  (binding [*ns* (create-ns 'incanter.core)]
    (eval (read-string (str "($= " expr-str ")")))))

(defroutes handler
  (GET "/:expr" [expr] (pr-str (eval-math expr)))
  (GET "/err/:status" [status] {:status (Integer. status)}))

(def app
  (-> #'handler
      (wrap-bounce-favicon)))
