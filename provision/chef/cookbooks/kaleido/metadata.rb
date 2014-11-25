name             'kaleido'
maintainer       'Jeff Blaisdell'
maintainer_email 'blaisdje@yahoo.com'
description      'Installs/Configures Kaleido'
long_description 'Installs/Configures Kaleido'
version          '0.1.0'
supports 'ubuntu', ">= 12.04"

recipe "kaleido::mysql", "Installs MySql."
recipe "kaleido::java", "Installs Java."

depends "java" , "~> 1.22.0"
depends "mysql", "~> 5.1.8"
depends "database", "~> 2.1.6"
depends "nginx", "~> 2.6.2"
