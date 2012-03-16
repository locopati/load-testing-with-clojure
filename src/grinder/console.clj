(ns grinder.console
  (:import net.grinder.console.client.ConsoleConnectionFactory)
  )

(defn remote-run []
  (let [host "localhost"
        port 6372
        cnx (.connect (ConsoleConnectionFactory.) host port)]
    (while (= 0 (.getNumberOfAgents cnx)) (java.lang.Thread/sleep 1000))
    (.startWorkerProcesses cnx nil)))