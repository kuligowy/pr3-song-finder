/**
 * Created by mtkl on 2017-07-03.
 */
import React from 'react'
import {Pagination} from "react-bootstrap";

class SongPager extends React.Component{

   constructor(props){
        super(props)
   }


  render() {
   return <Pagination
       bsSize="medium"
       items={this.props.totalPages}
       activePage={this.props.number + 1}
       onSelect={this.props.handleSelect}/>
  }
}

export default SongPager;