package com.challenge.vote.infra.docs;

import com.challenge.vote.application.errors.handle.HttpExceptionHandlerOutput;
import com.challenge.vote.application.usecases.vote.ComputeVoteOutput;
import com.challenge.vote.application.usecases.vote.DoVoteInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Vote")
public interface VoteOpenApi {

    @ApiOperation("Register vote")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Vote success registered"),
            @ApiResponse(code = 404, message = "Session not found", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 400, message = "Some posted data is incorrect", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 500, message = "Something unexpected happens", response = HttpExceptionHandlerOutput.class)})
    void create(@RequestBody final DoVoteInput input);

    @ApiOperation("Compute votes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Vote success computed"),
            @ApiResponse(code = 404, message = "Session not found", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 400, message = "Some posted data is incorrect", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 500, message = "Something unexpected happens", response = HttpExceptionHandlerOutput.class)})
    ComputeVoteOutput compute(@PathVariable("sessionId") Long sessionId);
}
