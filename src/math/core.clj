(ns math.core
  (:use clojure.set
	compojure.core
        incanter.core
        [clj-http.util :only [url-decode]])
  (:require [compojure.route :as route]
            [compojure.handler :as handler])
)

(defn eval-math [expr-str]
  (binding [*ns* (create-ns 'incanter.core)]
    (eval (read-string (str "($= " expr-str ")")))))

(defroutes main-routes
	(GET "/:expr" [expr] (pr-str (eval-math expr)))
	(route/not-found "<h1>404 Page Not Found</h1>")
)

(def app
	(handler/site main-routes))
