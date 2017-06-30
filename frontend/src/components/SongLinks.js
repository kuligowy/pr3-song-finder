/**
 * Created by mtkl on 2017-06-30.
 */
import React from 'react'

class SongLinks extends React.Component{
    constructor(props){
        super(props)
    }
    getIframelyHtml() {
    // If you use embed code from API
    return {__html: "http://www.youtube.com/embed/"+this.props.link.videoId};

    // Alternatively, if you use plain embed.js approach without API calls:
    // return {__html: '<a href="' + this.url + '" data-iframely-url></a>'};
    // no title inside <a> eliminates the flick
    }
    render(){

        return (
            <div dangerouslySetInnerHTML={this.getIframelyHtml}></div>
        )
    }
}


export default SongLinks;