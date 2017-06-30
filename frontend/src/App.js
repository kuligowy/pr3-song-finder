import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import BroadcastWrapper from './components/BroadcastWrapper'
import axios from 'axios'
class App extends Component {

    constructor(props){
        super(props)
        var source = [{
            id: 2,
            lastUpdated: 1498810584288,
            inserted: 1498810584288,
            comparator: { },
            start: "2017-06-30",
            stop: "2017-06-30",
            title: "Trójkowy Budzik ",
            songs: [
                {
                    id: 2,
                    links: [],
                    comparator: { },
                    title: "Nieprawda",
                    albumTitle: null,
                    duration: 30,
                    start: "2017-06-30",
                    artist: "ANIA DĄBROWSKA"
                },
                {
                    id: 3,
                    links: [ ],
                    comparator: { },
                    title: "Tak daleko stąd, tak blisko",
                    albumTitle: null,
                    duration: 0,
                    start: "2017-06-30",
                    artist: "Wodecki Zbigniew"
                }
            ]
        },{
            id: 4,
            lastUpdated: 1498810584288,
            inserted: 1498810584288,
            comparator: { },
            start: "2017-06-30",
            stop: "2017-06-30",
            title: "Trójkowy Budzik 2",
            songs:[]
            }
        ];
        this.state= {broadcasts: source};
    }


    componentDidMount(){
        var that = this;
        var broadcasts = axios.get("http://localhost:8080/api/songs").then(function(success){
            that.setState({
                broadcasts: success.data
            });
        },function(error){
            console.log(error);
        })
        console.log("Brdcst: "+this.state)

    }


  render() {
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
          <BroadcastWrapper broadcasts={this.state.broadcasts}></BroadcastWrapper>
      </div>
    );
  }
}

export default App;
