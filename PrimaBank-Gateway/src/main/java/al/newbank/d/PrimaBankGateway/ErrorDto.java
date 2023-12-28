package al.newbank.d.PrimaBankGateway;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    @Builder.Default private long timestamp = System.currentTimeMillis();
    private int status;
    private String error;
    private String path;

    public ErrorDto(int status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = System.currentTimeMillis();
    }
}