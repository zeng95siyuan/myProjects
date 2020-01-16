import  IEXClient  from 'iex-api'
import * as _fetch from 'isomorphic-fetch'

const iex = new IEXClient(_fetch)
iex.stockCompany('AAPL')
  .then(company => console.log(company.symbol))
