
def site_cookbooks(site_cookbooks)
  site_cookbooks.each { |name|
    site_cookbooks_path = '/opt/chef-solo/provisioning/chef/cookbooks/'
    cookbook name, path: site_cookbooks_path + name
  }
end