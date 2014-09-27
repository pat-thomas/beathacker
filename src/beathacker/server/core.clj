(ns beathacker.server.core
  (:require [clojure.tools.nrepl.server :as nrepl]
            [cider.nrepl                :as cider]
            [overtone.live              :as overtone]
            [beathacker.server.ws       :as ws]
            [beathacker.server.app-loop :as app-loop]))

(def nrepl-server (atom nil))

(defn start-nrepl-server
  [server-atom port]
  (do (reset! nrepl-server (nrepl/start-server :port port :handler cider/cider-nrepl-handler))
      (println (format "nrepl server listening on port %s" port))
      :nrepl-server-started))

(defn main
  []
  (app-loop/run-app-loop! app-loop/metro
                          app-loop/handler)
  (ws/app)
  (start-nrepl-server nrepl-server 4321))


(comment
  (main)
)
