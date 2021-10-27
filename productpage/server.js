const Koa = require("koa");
const Logger = require("koa-logger");
const serve = require("koa-static");
const mount = require("koa-mount");
const proxy = require("koa-proxies")

const app = new Koa();

const static_pages = new Koa();
static_pages.use(serve(__dirname)); //serve the build directory
app.use(mount("/", static_pages));

const PORT = process.env.PORT || 3000;
const API_SERVER = process.env.API_SERVER || 'http://127.0.0.1:8000';

app.use(Logger());

const proxyOptions = {
  target: API_SERVER,
  logs: true,
  changeOrigin: true
}

app.use(proxy('/api', proxyOptions))

app.listen(PORT, function () {
  console.log("==> 🌎  Listening on port %s. Visit http://localhost:%s/", PORT,
      PORT);
});
