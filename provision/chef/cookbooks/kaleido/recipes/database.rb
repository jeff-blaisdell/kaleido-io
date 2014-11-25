
include_recipe "database::mysql"

# create connection info.
mysql_connection_info = {
  :host     => 'localhost',
  :username => 'root',
  :password => node['mysql']['server_root_password']
}

# Create a mysql database
mysql_database 'kaleido' do
  connection mysql_connection_info
  action :create
end

# Create a mysql user grant and grant all privileges.
mysql_database_user 'kaleido' do
  connection mysql_connection_info
  host          '%'
  password   'password'
  action     [:create,:grant]
end