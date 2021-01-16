(ns notanamericanpresident.dataset
  (:require [clojure.edn :as e]))

(defn dataset []
  (->
   "resources/public/dataset.edn"
   (slurp)
   (e/read-string)))
