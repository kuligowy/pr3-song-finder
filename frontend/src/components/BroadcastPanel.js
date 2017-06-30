/**
 * Created by mtkl on 2017-06-30.
 */
import React from 'react'
import SongCard from './SongCard'
import {Panel} from "react-bootstrap";
class BroadcastPanel extends React.Component {

    constructor(props){
        super(props)
    }

    render(){
       return(
        <Panel  header={this.props.broadcast.start+ " "+this.props.broadcast.title}>
            {
                this.props.broadcast.songs.map(
                    song => <SongCard key={song.id} songEntry ={song}></SongCard>)
            }


        </Panel>
       )
    }
}

export default BroadcastPanel;