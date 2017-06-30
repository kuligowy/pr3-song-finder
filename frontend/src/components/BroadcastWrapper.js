/**
 * Created by mtkl on 2017-06-30.
 */
import React from 'react'
import BroadcastPanel from './BroadcastPanel'

class BroadcastWrapper extends React.Component {

    constructor(props){
        super(props)
    }

    render(){
        return <div>
            {this.props.broadcasts.map(b => (<BroadcastPanel key={b.id} broadcast={b} ></BroadcastPanel>))}

        </div>
    }

}

// const BroadcastWrapper = (broadcasts) =>{
//     <div>
//         broadcasts.map(b => (<span>{b.id}</span>))
//     </div>
// }

export default BroadcastWrapper;