class User < ApplicationRecord
  validates :index, presence: true, length: { is: 6 }, uniqueness: true
  has_secure_password
  validates :password, presence: true, length: { minimum: 6 }
end
