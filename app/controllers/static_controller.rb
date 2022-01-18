class StaticController < ApplicationController
  def index
    @users = User.all
  end

  def feed

  end
end
