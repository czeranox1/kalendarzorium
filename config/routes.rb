Rails.application.routes.draw do
  root to: 'static#index'

  #get    'index',    to: redirect('home/index')
  get    '/login',   to: 'sessions#new'
  post   '/login',   to: 'sessions#create'
  get   '/logout',  to: 'sessions#destroy'
  get    '/feed',   to: 'static#feed'
  #get    '/logout',  to: 'static#index'
  #root 'home#index'

  resources :users

  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  # Defines the root path route ("/")
  # root "articles#index"
end
