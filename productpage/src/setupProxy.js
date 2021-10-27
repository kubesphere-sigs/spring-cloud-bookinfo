const {createProxyMiddleware} = require('http-proxy-middleware');

const API_SERVER = process.env.API_SERVER || 'http://127.0.0.1:8000';

module.exports = function (app) {
  app.use(createProxyMiddleware('/api', {
    target: API_SERVER,
    changeOrigin: true,
  }));
};