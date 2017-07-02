/**
 * Created by mtkl on 2017-06-30.
 */
import React from 'react'
import {Col, Row} from "react-bootstrap";
import SongLink from './SongLinks'
class SongCard extends React.Component {

    constructor(props){
        super(props)
    }

    generateLinks(){
      return this.props.songEntry.links.map( (link)=>
                <SongLink key={link.videoId} videoId = {link.videoId}></SongLink>
        )
    }

    render(){

        return (
            <Row key={this.props.songEntry.id} >
                <Col md={4}>
                    {this.props.songEntry.artist} -    {this.props.songEntry.title}
                </Col>
                <Col md={8}>
                    <ul>
                        {this.generateLinks()}
                    </ul>
                </Col>
            </Row>
            )
        }
}

export default SongCard;