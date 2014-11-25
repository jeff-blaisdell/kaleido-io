node.set['mysql']['server_root_password'] = 'password1'
node.set['mysql']['server_debian_password'] = 'password1'
node.set['mysql']['port'] = '3306'
node.set['mysql']['data_dir'] = '/data'

mysql_service 'mysql' do
  version '5.5'
  action :create
end