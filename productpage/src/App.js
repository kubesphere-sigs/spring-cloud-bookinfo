import './App.css';
import 'jquery/src/jquery';
import 'bootstrap/dist/css/bootstrap-theme.min.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import {Component} from "react";
import _ from "lodash";

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      data: null,
    };
  }

  componentDidMount() {
    fetch('/api/v1/products/1')
    .then(response => {
      let state = this.state
      state.product = {ok: response.ok, statusText: response.statusText}
      this.setState(state)
      if (response.ok) {
        return response.json()
      }
    }).then(data => {
      let state = this.state
      state.product.data = data
      this.setState(state)
    }, error => {
      let state = this.state
      state.product = {ok: false, statusText: error}
      this.setState(state)
    })

    fetch('/api/v1/products/1/reviews')
    .then(response => {
      let state = this.state
      state.reviews = {ok: response.ok, statusText: response.statusText}
      this.setState(state)
      if (response.ok) {
        return response.json()
      }
    }).then(data => {
      let state = this.state
      state.reviews.data = data
      this.setState(state)
    }, error => {
      let state = this.state
      state.reviews = {ok: false, statusText: error}
      this.setState(state)
    })
  }

  renderProductInfo = () => {
    const {product} = this.state

    if (!product) {
      return null
    }

    if (product.data) {
      return (<div className="col-md-12">
            <h3 className="text-center text-primary">
              {product.data.title}
            </h3>
            <p dangerouslySetInnerHTML={{
              __html: "Summary: " + product.data.descriptionHTML
            }}/>
          </div>
      )
    }
  }

  renderProductDetails = () => {
    const {product} = this.state

    if (!product) {
      return null
    }

    if (product.data) {
      const details = product.data
      console.log(details)
      return (
          <div className="col-md-6">
            <h4 className="text-center text-primary">Book Details</h4>
            <dl>
              <dt>Type:</dt>
              {details.type}
              <dt>Pages:</dt>
              {details.pages}
              <dt>Publisher:</dt>
              {details.publisher}
              <dt>Language:</dt>
              {details.language}
              <dt>ISBN-10:</dt>
              {details.isbn_10}
              <dt>ISBN-13:</dt>
              {details.isbn_13}
            </dl>
          </div>
      )
    } else {
      return <div className="col-md-6">
        <h4 className="text-center text-primary">Error fetching product
          details!</h4>
      </div>
    }
  }

  renderReviews = () => {
    const {reviews} = this.state

    if (!reviews) {
      return null
    }

    if (reviews.data) {
      return (
          <div className="col-md-6">
            <h4 className="text-center text-primary">Book Reviews</h4>
            {reviews.data.map((item, i) => {
              return <blockquote>
                  <p>{item.text}</p>
                  <small>{item.reviewer}</small>
                  {function() {
                    if (item.rating) {
                      return <font color={item.rating.color}>
                        {_.times(item.rating.stars, (i) => (
                            <span className="glyphicon glyphicon-star"/>
                        ))}
                        {_.times(5 - item.rating.stars, (i) => (
                            <span className="glyphicon glyphicon-star-empty"/>
                        ))}
                      </font>
                    }
                  }()}
                </blockquote>
              })}
          </div>
      )
    }else{
      return <h4 className="text-center text-primary">Error fetching product
        reviews!</h4>
    }
  }

  render() {
    return (
        <div>
          <nav className="navbar navbar-inverse navbar-static-top">
            <div className="container">
              <div className="navbar-header">
                <a className="navbar-brand" href="/">BookInfo Sample</a>
              </div>
            </div>
          </nav>
          <div className="container-fluid">
            <div className="row"> {
              this.renderProductInfo()
            }
            </div>
            <div className="row">
              {this.renderProductDetails()}
              {this.renderReviews()}
            </div>
          </div>
        </div>)
  }
}


