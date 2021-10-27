const {resolve} = require('path')

const root = path => resolve(__dirname, `../${path}`)

module.exports = {
  entry: {
    server: './server.js',
  },
  output: {
    path: root('dist/'),
    publicPath: '/',
    filename: '[name].js',
    libraryTarget: 'commonjs',
  },
  target: 'node',
  node: {
    // Need this when working with express, otherwise the build fails
    __dirname: false, // if you don't put this is, __dirname
    __filename: false, // and __filename return blank or /
  },
  optimization: {
    minimize: false,
  },
  module: {
    rules: [
      {
        test: /\.(yml|html|css|svg|properties|ttf|otf|eot|woff2?)(\?.+)?$/,
        use: {
          loader: 'file-loader',
          options: {
            name: '[path][name].[ext]',
          },
        },
      },
    ],
  },
  mode: "production"
}