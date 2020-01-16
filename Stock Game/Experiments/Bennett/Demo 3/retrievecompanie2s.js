var request = require('request');
var node_ssh = require('node-ssh');
var fs = require('fs');
var ssh = new node_ssh();

const createCsvWriter = require('csv-writer').createObjectCsvWriter;
const csvWriter = createCsvWriter({
    path: 'companies.csv',
    header: [
        { id: 'symbol', title: 'Symbol' }
    ]
});

var url = "https://api.iextrading.com/1.0/ref-data/symbols?filter=symbol";
request.get(url, (error, response, body) => {
    if (error) {
        throw error;
    }
    list = JSON.parse(body);
    csvWriter.writeRecords(list);
    scp(list);
});

function scp(list) {
    ssh.connect({
        host: 'cs309-jr-4.misc.iastate.edu',
        port: 22,
        readyTimeout : 99999,
        username: 'bray',
        password: '504044Br'
    }).then(function () {
        ssh.putFile('companies.csv', '/home/bray').then(function () {
            console.log("The File thing is done")
        }, function (error) {
            console.log("Something's wrong")
            console.log(error)
        });
    });
}
