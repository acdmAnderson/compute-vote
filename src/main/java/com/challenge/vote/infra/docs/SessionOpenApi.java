package com.challenge.vote.infra.docs;

import com.challenge.vote.application.errors.handle.HttpExceptionHandlerOutput;
import com.challenge.vote.application.usecases.session.CreateSessionInput;
import com.challenge.vote.application.usecases.session.CreateSessionOutput;
import com.challenge.vote.application.usecases.session.GetSessionOutput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "Session")
public interface SessionOpenApi {

    @ApiOperation("Session creation")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Session success created", response = CreateSessionOutput.class),
            @ApiResponse(code = 400, message = "Some posted data is incorrect", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 500, message = "Something unexpected happens", response = HttpExceptionHandlerOutput.class)})
    CreateSessionOutput create(@RequestBody CreateSessionInput input);

    @ApiOperation("Get session by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Session success found", response = GetSessionOutput.class),
            @ApiResponse(code = 404, message = "Session not found", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 400, message = "Some posted data is incorrect", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 500, message = "Something unexpected happens", response = HttpExceptionHandlerOutput.class)})
    GetSessionOutput getSessionById(@PathVariable("sessionId") Long sessionId);

    @ApiOperation("Open session")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Session success opened", response = GetSessionOutput.class),
            @ApiResponse(code = 404, message = "Session not found", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 400, message = "Some posted data is incorrect", response = HttpExceptionHandlerOutput.class),
            @ApiResponse(code = 500, message = "Something unexpected happens", response = HttpExceptionHandlerOutput.class)})
    void open(@PathVariable("sessionId") Long sessionId);
}
