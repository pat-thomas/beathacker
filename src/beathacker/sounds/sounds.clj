(ns beathacker.sounds.sounds
  (:require [overtone.live :as overtone :refer [definst]]))

(definst sin-wave
  [freq 880 attack 0.1 sustain 0.4 release 0.2 vol 0.4]
  (* (overtone/env-gen (overtone/lin attack sustain release) 1 1 0 1 overtone/FREE)
     (overtone/sin-osc freq)
     vol))
