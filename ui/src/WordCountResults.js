import React, {Component} from 'react';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';

class WordCountResults extends Component {
  render() {
    return (
      <Grid container justify="space-around">
          <Card>
            <CardContent>
              <Typography variant="headline" component="h2">
                Total Word Count: {this.props.totalWordCount}
              </Typography>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Word</TableCell>
                    <TableCell numeric>Count</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.props.counts.map(n => {
                    return (
                      <TableRow key={n.word}>
                        <TableCell component="th" scope="row">{n.word}</TableCell>
                        <TableCell numeric>{n.count}</TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </Grid>
    );
  }
}
export default WordCountResults;