(ns notanamericanpresident.core
  (:require
   [reagent.dom :as rdom]
   ))

;; -------------------------
;; Page components


(defn current-page []
  (fn []
    [:div.body
     [:h1 "American president generator" [:small "Your very own leader of the free world"]]]))

;; -------------------------
;; Initialize app

(defn mount-root []
  (rdom/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
