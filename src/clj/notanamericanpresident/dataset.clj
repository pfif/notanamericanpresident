(ns notanamericanpresident.dataset
  (:require [clojure.data.json :as json]))

(defn dataset []
  (->
   "resources/public/dataset.json"
   (slurp)
   (json/read-str)))
