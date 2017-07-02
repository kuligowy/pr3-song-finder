/**
 * Created by mtkl on 2017-06-30.
 */
import React from 'react'
import YouTube from 'react-youtube'

class SongLinks extends React.Component{
    constructor(props){
        super(props)
    }

    loadasync(){
        // var tag = document.createElement('script');
        // tag.src = "https://www.youtube.com/player_api";
        // var firstScriptTag = document.getElementsByTagName('script')[0];
        // firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
        //
        // // Replace the 'ytplayer' element with an <iframe> and
        // // YouTube player after the API code downloads.
        // var player;
        // function onYouTubePlayerAPIReady() {
        //     player = new YT.Player('ytplayer', {
        //         height: '360',
        //         width: '640',
        //         videoId: 'M7lc1UVf-VE'
        //     });
        // }
    }

    render(){
        const opts = {
            height: '200',
            width: '200',
            playerVars: { // https://developers.google.com/youtube/player_parameters
                autoplay: 0
            }
        };
        return (
            <YouTube
                videoId={this.props.videoId}                  // defaults -> null
                opts={opts}
            />
        )
    }
}


export default SongLinks;