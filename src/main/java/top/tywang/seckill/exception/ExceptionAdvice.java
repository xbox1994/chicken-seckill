package top.tywang.seckill.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import top.tywang.seckill.result.CodeMsg;
import top.tywang.seckill.result.Result;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Result<String> handleOtherException(Throwable e) {
        log.error("system error", e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @ExceptionHandler(GlobalException.class)
    public Result<String> handleGlobalException(GlobalException e) {
        log.error(e.getCm().toString());
        return Result.error(e.getCm());
    }

    @ExceptionHandler(BindException.class)
    public Result<String> handleBindException(BindException e) {
        log.error(e.getMessage());
        List<ObjectError> errors = e.getAllErrors();
        ObjectError error = errors.get(0);
        String msg = error.getDefaultMessage();
        return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
    }
}
