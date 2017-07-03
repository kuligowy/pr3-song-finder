import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import BroadcastWrapper from './components/BroadcastWrapper'
import axios from 'axios'
import BroadcastChooser from './components/BroadcastChooser'
import Song from './components/Song'
import {Pagination} from 'react-bootstrap'

String.prototype.format = function (){
    var args = arguments;
    return this.replace(/\{\{|\}\}|\{(\d+)\}/g, function (curlyBrack, index) {
        return ((curlyBrack == "{{") ? "{" : ((curlyBrack == "}}") ? "}" : args[index]));
    });
};

class App extends Component {




    constructor(props){
        super(props)
        this.state = {
            broadcast: {},
            broadcasts: [],
            pageSize: 5,
            songs: {content: []}
        }
        this.API_BASIC = "http://localhost:8080/api/broadcast?sort=start,desc";
        this.API= "http://localhost:8080/api/broadcast/{0}?size=6&page{1}&sort=start,asc";

    }

    componentDidMount(){
        var that = this;
        axios.get(this.API_BASIC).then(function(success){
            that.setState({
                broadcasts: success.data,
                songs: {content: []}
            });
        },function(error){
            console.log(error);
        })
    }

    handleBroadcastChooser(event){
        var b_id = event.target.value;
        var that = this;
        var broadcasts = axios.get(this.API.format(b_id,1))
            .then(success=>{
            this.setState({
                broadcastId: b_id,
                songs: success.data
            });
        },error => {
            console.log(error);
        })
    }

    handleSelectPage(page){
        let b = this.state.broadcast;
        let requestPage = page-1;
        var broadcasts = axios.get(this.API.format(b.id,requestPage)).then(success=>{
            this.setState({
                songs: success.data
            });
        },error => {
            console.log(error);
        })
    }

  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>PR3-Song-Finder</h2>
        </div>
        <p className="App-intro">

        </p>
          <BroadcastChooser broadcasts={this.state.broadcasts} click={this.handleBroadcastChooser.bind(this)} />
          {/*<BroadcastWrapper broadcast={this.state.broadcast} songs={this.state.songs}></BroadcastWrapper>*/}
          <Song songs={this.state.songs.content}></Song>
          <Pagination
              bsSize="medium"
              items={this.state.songs.totalPages}
              activePage={this.state.songs.number + 1}
              onSelect={this.handleSelectPage.bind(this)}/>
      </div>

    );
  }
}

export default App;
