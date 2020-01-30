docker --tls --tlscacert ./.github/domain.crt --tlscert ./.github/domain.crt --tlskey ./.github/domain.key login -u ${{ secrets.USERNAME }} -p ${{ secrets.PASSWORD }} 78.47.167.60:5000

#sudo mkdir /etc/docker/certs.d/78.47.167.60:5000
#sudo cp ./.github/scripts/domain.key /etc/docker/certs.d/78.47.167.60:5000/domain.key
#sudo cp ./.github/scripts/domain.crt /etc/docker/certs.d/78.47.167.60:5000/domain.cert