(ns beathacker.core
  (:require [clojure.tools.nrepl.server   :as nrepl]
            [cider.nrepl                  :as cider]
            [overtone.live                :as overtone]
            [beathacker.ws                :as ws]
            [beathacker.app-loop          :as app-loop]))

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
  (events/fire-event! :event-fired {:type :note
                                    :sound-type :sin
                                    :args [660 0.2]})
)
