/**
 * Created by mtkl on 2017-07-03.
 */


import React from 'react'
import {Row,Col} from 'react-bootstrap'
import SongLink from './SongLinks'
const Song = props =>{
    //console.log(songs);


    var songs = props.songs.map(s => <Row key={s.id} >
        <Col md={4}>
            {s.artist} -    {s.title}
        </Col>
        <Col md={8}>
            <ul>
                {/*{s.links.map( (link)=>*/}
                {/*<SongLink key={link.videoId} videoId = {link.videoId}></SongLink>*/}
                {/*)} */}
                <SongLink key={s.links[0].videoId} videoId = {s.links[0].videoId}></SongLink>

            </ul>
        </Col>
    </Row>);
    return <div>{songs}</div>

}

export default  Song