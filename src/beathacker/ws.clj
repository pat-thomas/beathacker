(ns beathacker.ws
  (:require [org.httpkit.server :as http]
            [compojure.core     :as compojure]
            [compojure.handler  :as handler]
            [compojure.route    :as route]))

(compojure/defroutes app-routes
  (compojure/GET "/ping" [] "pong\n"))

(defn app
  []
  (http/run-server (handler/site #'app-routes) {:port 8080}))

(comment
(app)
)

