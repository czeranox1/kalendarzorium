class User < ApplicationRecord
  validates :index, presence: true, length: { is: 6 }, uniqueness: true
  has_secure_password
  validates :password, presence: true, length: { minimum: 6 }
  has_secure_token

  def invalidate_token
    self.update_columns(token: nil)
  end


end
