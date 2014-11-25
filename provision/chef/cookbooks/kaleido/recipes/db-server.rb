kaleido = node["kaleido"]
config  = data_bag_item(kaleido["env"], "mysql")

# Initialize Configuration
node.set['mysql']['service_name'] = config['service_name']
node.set['mysql']['server_root_password'] = config['server_root_password']
node.set['mysql']['data_dir'] = config['data_dir']
node.set['mysql']['port'] = config['port']

# Setup a MySql server.
include_recipe 'mysql::server'

# Create a database.
mysql_connection_info = {
  :host     => 'localhost',
  :username => 'root',
  :password => node['mysql']['server_root_password']
}

mysql_database 'kaleido' do
  connection mysql_connection_info
  action :create
end

