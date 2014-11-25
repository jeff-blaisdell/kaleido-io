
node.set['java']['install_flavor'] = 'oracle'
node.set['java']['jdk_version'] = '7'
node.set['java']['oracle']['accept_oracle_download_terms'] = true

include_recipe 'java'