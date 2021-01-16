(ns notanamericanpresident.core
  (:require
   [reagent.dom :as rdom]
   [notanamericanpresident.dataset :refer [dataset]]
   [notanamericanpresident.algorithm :refer [generate-president]]
   [clojure.string :as s]))

;; -------------------------
;; Page components


(defn current-page []
  (fn []
    [:div.body
     [:h1 "American president generator" [:small "Your very own leader of the free world!"]]
     (let [president (generate-president dataset)]
       [:p (s/join " " [(:first-name president) (:last-name president)])]
       )]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
