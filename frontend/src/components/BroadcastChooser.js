/**
 * Created by mtkl on 2017-07-03.
 */
import React from 'react'
import {FormGroup,ControlLabel,FormControl} from 'react-bootstrap'
class BroadcastChooser extends React.Component{


        constructor(props){
            super(props);

        }

        render() {
            var list =  this.props.broadcasts.map(b =>
                <option key={b.id} value={b.id}>{b.start.slice(11)} - {b.stop.slice(11)} {b.title} ({b.songsSize})</option>
            )

          return  (<FormGroup controlId="formControlsSelect">
               <ControlLabel>Select</ControlLabel>
               <FormControl componentClass="select" placeholder="select" onChange={(change,evt) => this.props.click(change,evt)}>
                   {list}
               </FormControl>
           </FormGroup>
            )
       }

}

export default BroadcastChooser;