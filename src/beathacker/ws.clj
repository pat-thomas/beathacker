(ns beathacker.ws
  (:require [org.httpkit.server :as http]
            [compojure.core     :as compojure]
            [compojure.handler  :as handler]
            [compojure.route    :as route]))

(defn handle-core-api-req
  [{:keys [params] :as req}]
  "implement-me\n")

(compojure/defroutes app-routes
  (compojure/GET  "/ping" [] "pong\n")
  (compojure/POST "/core" [] #'handle-core-api-req))

(defn app
  []
  (let [port 8080]
    (do (println "Web server listening on port" port)
        (http/run-server (handler/site #'app-routes) {:port port}))))

(comment
  (app)
)

